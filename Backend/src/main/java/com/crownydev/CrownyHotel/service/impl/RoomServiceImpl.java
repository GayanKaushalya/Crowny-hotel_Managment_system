// PASTE THIS FULL CODE INTO YOUR RoomServiceImpl.java FILE

package com.crownydev.CrownyHotel.service.impl;

import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.dto.RoomDTO;
import com.crownydev.CrownyHotel.entity.Room;
import com.crownydev.CrownyHotel.exception.OurException;
import com.crownydev.CrownyHotel.repo.RoomRepository;
import com.crownydev.CrownyHotel.service.interfac.IFileStorageService;
import com.crownydev.CrownyHotel.service.interfac.IRoomService;
import com.crownydev.CrownyHotel.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private final IFileStorageService fileStorageService;

    public RoomServiceImpl(RoomRepository roomRepository, IFileStorageService fileStorageService) {
        this.roomRepository = roomRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String roomDescription) {
        Response response = new Response();
        try {
            String photoFileName = fileStorageService.saveFile(photo);
            Room room = new Room();
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(roomDescription);
            room.setRoomPhotoUrl(photoFileName);
            Room savedRoomEntity = roomRepository.save(room);
            RoomDTO savedRoomDTO = Utils.mapRoomEntityToRoomDTO(savedRoomEntity);
            response.setStatusCode(201);
            response.setMessage("success");
            response.setRoom(savedRoomDTO);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding new room: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try {
            List<Room> roomEntities = roomRepository.findAll();
            // *** THIS IS THE KEY: We map the list of entities to a list of DTOs ***
            List<RoomDTO> roomDTOs = roomEntities.stream()
                    .map(Utils::mapRoomEntityToRoomDTO)
                    .collect(Collectors.toList());
            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoomList(roomDTOs);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all rooms: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try {
            Room roomEntity = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(roomEntity);
            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoom(roomDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting room by id: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();
        try {
            Room roomEntity = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            if (roomType != null) roomEntity.setRoomType(roomType);
            if (roomPrice != null) roomEntity.setRoomPrice(roomPrice);
            if (description != null) roomEntity.setRoomDescription(description);
            if (photo != null && !photo.isEmpty()) {
                if (roomEntity.getRoomPhotoUrl() != null && !roomEntity.getRoomPhotoUrl().isEmpty()) {
                    fileStorageService.deleteFile(roomEntity.getRoomPhotoUrl());
                }
                String newPhotoFileName = fileStorageService.saveFile(photo);
                roomEntity.setRoomPhotoUrl(newPhotoFileName);
            }
            Room updatedRoomEntity = roomRepository.save(roomEntity);
            RoomDTO updatedRoomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoomEntity);
            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoom(updatedRoomDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating room: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            if (room.getRoomPhotoUrl() != null && !room.getRoomPhotoUrl().isEmpty()) {
                fileStorageService.deleteFile(room.getRoomPhotoUrl());
            }
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("success");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting room: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();
        try {
            List<Room> roomEntities = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOs = roomEntities.stream()
                    .map(Utils::mapRoomEntityToRoomDTO)
                    .collect(Collectors.toList());
            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoomList(roomDTOs);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting available rooms: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        try {
            List<Room> roomEntities = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOs = roomEntities.stream()
                    .map(Utils::mapRoomEntityToRoomDTO)
                    .collect(Collectors.toList());
            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoomList(roomDTOs);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting available rooms by date and type: " + e.getMessage());
        }
        return response;
    }
}