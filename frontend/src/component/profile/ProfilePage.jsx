// PASTE THIS FULL CODE INTO YOUR ProfilePage.jsx FILE

import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const ProfilePage = () => {
    const [user, setUser] = useState(null);
    const [bookings, setBookings] = useState([]); // Separate state for bookings
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                // Step 1: Fetch the user's profile information
                const profileResponse = await ApiService.getUserProfile();
                if (profileResponse.statusCode === 200) {
                    setUser(profileResponse.user);

                    // Step 2: If user is found, use their ID to fetch their bookings
                    const bookingsResponse = await ApiService.getUserBookings(profileResponse.user.id);
                    if (bookingsResponse.statusCode === 200) {
                        // The backend returns a 'bookingList'
                        setBookings(bookingsResponse.bookingList || []);
                    } else {
                        setError(bookingsResponse.message);
                    }
                } else {
                    setError(profileResponse.message);
                }

            } catch (error) {
                setError(error.response?.data?.message || error.message);
            }
        };

        fetchUserData();
    }, []); // Empty dependency array means this runs once on component mount

    const handleLogout = () => {
        ApiService.logout();
        navigate('/login'); // Navigate to login page after logout
        window.location.reload(); // Force a reload to update navbar, etc.
    };

   // THIS IS THE NEW, CORRECT CODE
    const handleEditProfile = () => {
    navigate('/edit-profile');
    };

    return (
        <div className="profile-page">
            {user && <h2>Welcome, {user.name}</h2>}
            <div className="profile-actions">
                <button className="edit-profile-button" onClick={handleEditProfile}>Edit Profile</button>
                <button className="logout-button" onClick={handleLogout}>Logout</button>
            </div>
            {error && <p className="error-message">{error}</p>}
            
            {user && (
                <div className="profile-details">
                    <h3>My Profile Details</h3>
                    <p><strong>Email:</strong> {user.email}</p>
                    <p><strong>Phone Number:</strong> {user.phoneNumber}</p>
                </div>
            )}

            <div className="bookings-section">
                <h3>My Booking History</h3>
                <div className="booking-list">
                    {bookings.length > 0 ? (
                        bookings.map((booking) => (
                            <div key={booking.id} className="booking-item">
                                <p><strong>Booking Code:</strong> {booking.bookingConfirmationCode}</p>
                                <p><strong>Check-in Date:</strong> {booking.checkInDate}</p>
                                <p><strong>Check-out Date:</strong> {booking.checkOutDate}</p>
                                <p><strong>Total Guests:</strong> {booking.totalNumOfGuest}</p>
                                <p><strong>Room Type:</strong> {booking.room.roomType}</p>
                                {/* You might need to construct the full URL if it's just a path */}
                                <img src={booking.room.roomPhotoUrl} alt="Room" className="room-photo" />
                            </div>
                        ))
                    ) : (
                        <p>No bookings found.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;