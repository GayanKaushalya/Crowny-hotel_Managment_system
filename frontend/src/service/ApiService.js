
import axios from "axios"

export default class ApiService {

    static BASE_URL = "http://localhost:8080"

    static getHeader() {
        const token = localStorage.getItem("token");
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        };
    }

    /** ========================= AUTH ========================= */

    static async registerUser(registration) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, registration)
        return response.data
    }

   static async loginUser(loginDetails) {
    const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails);
    
    // Check if the response contains the token and role
    if (response.data.token && response.data.role) {
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('role', response.data.role);
    }
    
    return response.data;
}

    /** ========================= USERS ========================= */

    static async getAllUsers() {
        const response = await axios.get(`${this.BASE_URL}/users/all`, {
            headers: this.getHeader()
        })
        return response.data
    }

    static async getUserProfile() {
        const response = await axios.get(`${this.BASE_URL}/users/my-info`, {
            headers: this.getHeader()
        })
        return response.data
    }

    static async getUser(userId) {
        const response = await axios.get(`${this.BASE_URL}/users/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }
    
    static async deleteUser(userId) {
        const response = await axios.delete(`${this.BASE_URL}/users/delete/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }
    static async updateUser(userId, userData) {
        const response = await axios.put(`${this.BASE_URL}/users/update/${userId}`, userData, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /** ========================= ROOMS ========================= */

    static async addRoom(formData) {
        const result = await axios.post(`${this.BASE_URL}/rooms/add`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }

    static async getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType) {
        const result = await axios.get(
            `${this.BASE_URL}/rooms/available-rooms-by-date-and-type?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`
        )
        return result.data
    }

    static async getRoomTypes() {
        const response = await axios.get(`${this.BASE_URL}/rooms/types`)
        return response.data
    }
    
    static async getAllRooms() {
        const result = await axios.get(`${this.BASE_URL}/rooms/all`)
        return result.data
    }
    
    static async getRoomById(roomId) {
        const result = await axios.get(`${this.BASE_URL}/rooms/${roomId}`, {
            // This header is required because the endpoint is now .authenticated()
            headers: this.getHeader()
        })
        return result.data
    }

    static async deleteRoom(roomId) {
        const result = await axios.delete(`${this.BASE_URL}/rooms/delete/${roomId}`, {
            headers: this.getHeader()
        })
        return result.data
    }

    static async updateRoom(roomId, formData) {
        const result = await axios.put(`${this.BASE_URL}/rooms/update/${roomId}`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }


    /** ========================= BOOKINGS ========================= */

    static async bookRoom(roomId, userId, booking) {
        const response = await axios.post(`${this.BASE_URL}/bookings/book-room/${roomId}/${userId}`, booking, {
            headers: this.getHeader()
        })
        return response.data
    }

    static async getAllBookings() {
        const result = await axios.get(`${this.BASE_URL}/bookings/all`, {
            headers: this.getHeader()
        })
        return result.data
    }

    static async getBookingByConfirmationCode(bookingCode) {
        const result = await axios.get(`${this.BASE_URL}/bookings/confirmation/${bookingCode}`)
        return result.data
    }

    static async cancelBooking(bookingId) {
        const result = await axios.delete(`${this.BASE_URL}/bookings/cancel/${bookingId}`, {
            headers: this.getHeader()
        })
        return result.data
    }

    // *** THIS IS THE NEWLY ADDED FUNCTION ***
    static async getUserBookings(userId) {
        const response = await axios.get(`${this.BASE_URL}/bookings/user/${userId}`, {
            headers: this.getHeader()
        });
        return response.data;
    }


    /** ========================= AUTHENTICATION CHECKER ========================= */
    
   // In ApiService.js


    static logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('role'); // This line is added
    }

    static isAuthenticated() {
        const token = localStorage.getItem('token')
        return !!token
    }

    static isAdmin() {
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isUser() {
        const role = localStorage.getItem('role')
        return role === 'USER'
    }
}