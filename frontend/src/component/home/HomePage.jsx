

import React, { useState } from "react";
import RoomResult from "../common/RoomResult";
import RoomSearch from "../common/RoomSearch";


import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
// Import Swiper styles
import 'swiper/css';
import 'swiper/css/navigation';

const roomsData = [
    { id: 1, image: "/assets/Images/room-1.png", price: 150, title: "Deluxe Room", description: "A spacious room with a beautiful view." },
    { id: 2, image: "/assets/Images/room-2.png", price: 200, title: "Suite", description: "Luxury suite with a separate living area." },
    { id: 3, image: "/assets/Images/room-3.png", price: 120, title: "Standard Room", description: "Comfortable and affordable." },
    { id: 4, image: "/assets/Images/room-4.png", price: 155, title: "Deluxe Sea View", description: "A spacious room with a beautiful sea view." },
];

const galleryData = [
    { id: 1, image: "/assets/Images/gallery-1.png", title: "Lobby" },
    { id: 2, image: "/assets/Images/gallery-2.png", title: "Swimming Pool" },
    { id: 3, image: "/assets/Images/gallery-3.png", title: "Restaurant" },
    { id: 4, image: "/assets/Images/gallery-4.png", title: "Gym" },
    { id: 5, image: "/assets/Images/gallery-5.png", title: "Spa" },
];


const HomePage = () => {
    // This state holds the rooms found after a search. It starts empty.
    const [roomSearchResults, setRoomSearchResults] = useState([]);
    
    // *** NEW: This state tracks if the user has clicked the search button. It starts as false. ***
    const [searchPerformed, setSearchPerformed] = useState(false);

    // This function is passed to RoomSearch and is called when a search is complete.
    const handleSearchResult = (results) => {
        setRoomSearchResults(results);
        // *** NEW: When we get results, we update the state to show that a search has happened. ***
        setSearchPerformed(true);
    };

   

    return (
        <div className="home">
            {/* HEADER / BANNER ROOM SECTION */}
            <section>
                <header className="header-banner">
                    <img src="./assets/images/banner-1.png" alt="Phegon Hotel" className="header-image" />
                    <div className="overlay"></div>
                    <div className="animated-texts overlay-content">
                        <h1>
                            Welcome to <span style={{ color: '#7fc142' }}>Crowny Hotel</span>
                        </h1>
                        <h3>Step into a haven of comfort and care</h3>
                    </div>
                </header>
            </section>

            {/* SEARCH/FIND AVAILABLE ROOM SECTION */}
            <RoomSearch handleSearchResult={handleSearchResult} />

            {/* *** THE FIX: We only render the RoomResult component IF searchPerformed is true. *** */}
            {searchPerformed && <RoomResult roomSearchResults={roomSearchResults} />}
            
            <section class="about top">
                <div class="container flex">
                     <div class="left">
                        <div class="heading">
                            <h1>WELCOME</h1>
                            <h2>Crowny Hotel</h2>
                        </div>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
                            aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                        <button class="primary-btn">ABOUT US</button>
                    </div>
                    <div class="right">
                         <img src="./assets/Images/about.png" alt=""/>
                    </div>
                </div>
            </section>

            <section className="rooms top">
                <div className="container">
                    <div className="heading">
                        <h1>EXPLORE</h1>
                        <h2>Our Rooms</h2>
                    </div>
                    
                    {/* The Swiper Carousel for rooms. We use owl-carousel1 class to apply your styles */}
                    <Swiper
                        modules={[Navigation]}
                        spaceBetween={30}
                        slidesPerView={3}
                        navigation
                        className="owl-carousel1"
                        breakpoints={{
                            320: { slidesPerView: 1 },
                            768: { slidesPerView: 2 },
                            1024: { slidesPerView: 3 },
                        }}
                    >
                        {roomsData.map((room) => (
                            <SwiperSlide key={room.id} className="items">
                                <div className="image">
                                    <img src={room.image} alt={room.title} />
                                </div>
                                <div className="text">
                                    <h2>{room.title}</h2>
                                    <div className="rate">
                                        <i className="fa fa-star"></i>
                                        <i className="fa fa-star"></i>
                                        <i className="fa fa-star"></i>
                                        <i className="fa fa-star"></i>
                                        <i className="fa fa-star"></i>
                                    </div>
                                    <p>{room.description}</p>
                                    <div className="flex_space">
                                        <h3>${room.price}<span> / per night</span></h3>
                                        <button className="primary-btn">BOOK NOW</button>
                                    </div>
                                </div>
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
            </section>

            <h4><a className="view-rooms-home" href="/rooms">All Rooms</a></h4>

            {/* =================================================================== */}
            {/* ================= NEW "OUR GALLERY" SECTION HERE ================== */}
            {/* =================================================================== */}
            <section className="gallery top">
                <div className="container">
                    <div className="heading">
                        <h1>PHOTOS</h1>
                        <h2>Our Gallery</h2>
                    </div>

                    <Swiper
                        modules={[Navigation]}
                        spaceBetween={10}
                        slidesPerView={4}
                        navigation
                        className="owl-carousel1"
                         breakpoints={{
                            320: { slidesPerView: 1 },
                            768: { slidesPerView: 2 },
                            1024: { slidesPerView: 4 },
                        }}
                    >
                        {galleryData.map((item) => (
                             <SwiperSlide key={item.id} className="items">
                                <div className="img">
                                    <img src={item.image} alt={item.title} />
                                    <div className="overlay">
                                        <span className="fa fa-plus"></span>
                                        <h3>{item.title}</h3>
                                    </div>
                                </div>
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
            </section>
   
            

            <h2 className="home-services">Services at <span className="phegon-color">Crowny Hotel</span></h2>

            {/* SERVICES SECTION */}
            <section className="service-section">
                <div className="service-card">
                    <img src="./assets/images/ac.png" alt="Air Conditioning" />
                    <div className="service-details">
                        <h3 className="service-title">Air Conditioning</h3>
                        <p className="service-description">Stay cool and comfortable throughout your stay with our individually controlled in-room air conditioning.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/mini-bar.png" alt="Mini Bar" />
                    <div className="service-details">
                        <h3 className="service-title">Mini Bar</h3>
                        <p className="service-description">Enjoy a convenient selection of beverages and snacks stocked in your room's mini bar with no additional cost.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/parking.png" alt="Parking" />
                    <div className="service-details">
                        <h3 className="service-title">Parking</h3>
                        <p className="service-description">We offer on-site parking for your convenience . Please inquire about valet parking options if available.</p>
                    </div>
                </div>
                <div className="service-card">
                    <img src="./assets/images/wifi.png" alt="WiFi" />
                    <div className="service-details">
                        <h3 className="service-title">WiFi</h3>
                        <p className="service-description">Stay connected throughout your stay with complimentary high-speed Wi-Fi access available in all guest rooms and public areas.</p>
                    </div>
                </div>
            </section>
            
            
            {/* AVAILABLE ROOMS SECTION */}
            <section>
                {/* This section can be used for other content if needed */}
            </section>
        </div>
    );
}

export default HomePage;