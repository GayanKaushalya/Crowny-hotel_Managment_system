

import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const EditProfilePage = () => {
    // State to hold the original user object (for ID and email)
    const [user, setUser] = useState(null);

    // State to manage the data in the form fields
    const [formData, setFormData] = useState({ name: '', phoneNumber: '' });

    // State for handling error and success messages
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    
    const navigate = useNavigate();

    // This hook runs once when the page loads to get the user's data
    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const response = await ApiService.getUserProfile();
                if (response.statusCode === 200) {
                    setUser(response.user);
                    // Pre-fill the form with the user's current data
                    setFormData({
                        name: response.user.name,
                        phoneNumber: response.user.phoneNumber
                    });
                } else {
                    setError(response.message);
                }
            } catch (err) {
                setError(err.message);
            }
        };
        fetchUserProfile();
    }, []); // The empty array [] ensures this runs only once

    // This function updates the form data as the user types
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    // This function is called when the user submits the form
    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevents the page from refreshing
        setError(null);
        setSuccessMessage('');

        try {
            // Call the updateUser function from our ApiService
            const response = await ApiService.updateUser(user.id, formData);
            if (response.statusCode === 200) {
                setSuccessMessage('Profile updated successfully!');
                // Wait 2 seconds, then navigate back to the profile page
                setTimeout(() => {
                    navigate('/profile');
                }, 2000);
            } else {
                setError(response.message);
            }
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        }
    };

    // Show a loading message until the user data is fetched
    if (!user) {
        return <p>Loading profile...</p>;
    }

    return (
        <div className="edit-profile-page">
            <h2>Edit My Profile</h2>
            {error && <p className="error-message" style={{color: "red"}}>{error}</p>}
            {successMessage && <p className="success-message" style={{color: "green"}}>{successMessage}</p>}
            
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="email">Email (cannot be changed)</label>
                    <input type="email" id="email" value={user.email} disabled />
                </div>
                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone Number</label>
                    <input
                        type="tel"
                        id="phoneNumber"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <button type="submit" className="btn-submit">Save Changes</button>
            </form>
        </div>
    );
};

export default EditProfilePage;