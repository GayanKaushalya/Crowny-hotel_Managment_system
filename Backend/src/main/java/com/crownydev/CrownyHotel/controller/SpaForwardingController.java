package com.crownydev.CrownyHotel.controller; // Or whatever your controller package is

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller handles server-side 404 errors by forwarding them to the
 * React application's entry point (index.html). This is crucial for making
 * client-side routing (like React Router) work correctly when a user refreshes
 * a page or directly navigates to a frontend route that Spring doesn't recognize.
 */
@Controller
public class SpaForwardingController implements ErrorController {

    /**
     * Forwards any request that results in a server-side error (like 404)
     * to the root path, which serves the index.html of the React app.
     */
    @RequestMapping("/error")
    public String handleError() {
        // Forward to the root, which will serve index.html
        return "forward:/index.html";
    }
}