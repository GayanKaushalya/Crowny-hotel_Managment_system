// PASTE THIS FULL CODE INTO YOUR RoomResult.jsx FILE

import React from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const RoomResult = ({ roomSearchResults }) => {
    const navigate = useNavigate();
    const isAdmin = ApiService.isAdmin();

    // A check to handle cases where the data might not be ready yet.
    if (!roomSearchResults || roomSearchResults.length === 0) {
        return <p>No rooms to display.</p>;
    }

    return (
        <section className="room-results">
            <div className="room-list">
                {roomSearchResults.map(room => {
                    // *** THE FIX IS HERE ***
                    // We have REMOVED the line: const imageUrl = ...
                    // We will now use room.roomPhotoUrl directly in the <img> tag below,
                    // because it already contains the full URL from the backend.

                    return (
                        <div key={room.id} className="room-list-item">
                            
                            {/* The src attribute now uses the prop directly */}
                            <img 
                                className='room-list-item-image' 
                                src={room.roomPhotoUrl} 
                                alt={room.roomType} 
                            />
                            
                            <div className="room-details">
                                <h3>{room.roomType}</h3>
                                <p>Price: ${room.roomPrice} / night</p>
                                <p>Description: {room.roomDescription}</p>
                            </div>

                            <div className='book-now-div'>
                                {isAdmin ? (
                                    <button
                                        className="edit-room-button"
                                        onClick={() => navigate(`/admin/edit-room/${room.id}`)}
                                    >
                                        Edit Room
                                    </button>
                                ) : (
                                    <button
                                        className="book-now-button"
                                        onClick={() => navigate(`/room-details-book/${room.id}`)}
                                    >
                                        View / Book Now
                                    </button>
                                )}
                            </div>
                        </div>
                    );
                })}
            </div>
        </section>
    );
}

export default RoomResult;