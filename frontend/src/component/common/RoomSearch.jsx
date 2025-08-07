import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ApiService from '../../service/ApiService';

const RoomSearch = ({ handleSearchResult }) => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [roomType, setRoomType] = useState('');
  const [roomTypes, setRoomTypes] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchRoomTypes = async () => {
      try {
        const types = await ApiService.getRoomTypes();
        setRoomTypes(types);
      } catch (error) {
        console.error('Error fetching room types:', error);
        setError("Could not load room types. Please refresh the page.");
      }
    };
    fetchRoomTypes();
  }, []);

  /**This method is used to show errors and clear them after a timeout */
  const showError = (message, timeout = 5000) => {
    setError(message);
    setTimeout(() => {
      setError('');
    }, timeout);
  };

  /**This function fetches available rooms from the database based on search criteria */
  const handleInternalSearch = async () => {
    if (!startDate || !endDate || !roomType) {
      showError('Please select a check-in date, check-out date, and room type.');
      return; // Use 'return' instead of 'return false'
    }

    if (startDate >= endDate) {
      showError('Check-out date must be after the check-in date.');
      return;
    }

    try {
      // Convert dates to the desired format (YYYY-MM-DD)
      const formattedStartDate = startDate.toISOString().split('T')[0];
      const formattedEndDate = endDate.toISOString().split('T')[0];
      
      // Call the API to fetch available rooms
      const response = await ApiService.getAvailableRoomsByDateAndType(formattedStartDate, formattedEndDate, roomType);

      // Assuming a successful call returns an object with a 'roomList' array
      if (response.roomList.length === 0) {
        showError('No rooms of the selected type are available for this date range.');
        // It's good practice to clear previous results if a new search yields nothing
        handleSearchResult([]); 
      } else {
        handleSearchResult(response.roomList);
        setError(''); // Clear any previous errors on success
      }

    } catch (error) {
      // ***************************************************************
      // ******************* THIS IS THE CORRECTED PART ****************
      // ***************************************************************

      // 1. Log the full error to the browser's console for debugging purposes.
      console.error("Failed to fetch available rooms:", error);

      // 2. Define a default error message for unexpected issues.
      let errorMessage = "An unexpected error occurred. Please try again later.";

      // 3. Safely check if the error object has a specific message from your backend.
      //    This prevents the "undefined" error.
      if (error.response && error.response.data && error.response.data.message) {
        errorMessage = error.response.data.message;
      } else if (error.message) {
        // This handles network errors or other generic JavaScript errors.
        errorMessage = error.message;
      }

      // 4. Display the clear, safe error message (and fix the typo: "Unown" -> "Unknown").
      showError("Unknown error occurred: " + errorMessage);
    }
  };

  return (
    <section>
      <div className="search-container">
        <div className="search-field">
          <label>Check-in Date</label>
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            dateFormat="dd/MM/yyyy"
            placeholderText="Select Check-in Date"
            minDate={new Date()} // Prevent selecting past dates
          />
        </div>
        <div className="search-field">
          <label>Check-out Date</label>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            dateFormat="dd/MM/yyyy"
            placeholderText="Select Check-out Date"
            minDate={startDate ? new Date(startDate.getTime() + 86400000) : new Date()} // Checkout must be after check-in
          />
        </div>

        <div className="search-field">
          <label>Room Type</label>
          <select value={roomType} onChange={(e) => setRoomType(e.target.value)}>
            <option disabled value="">
              Select Room Type
            </option>
            {roomTypes.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>
        <button className="home-search-button" onClick={handleInternalSearch}>
          Search Rooms
        </button>
      </div>
      {error && <p className="error-message">{error}</p>}
    </section>
  );
};

export default RoomSearch;