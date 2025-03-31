document.addEventListener("DOMContentLoaded", function () {
    const vehicleForm = document.getElementById("vehicleForm");
    const vehicleNumberInput = document.getElementById("vehicleNumber");

    // Leaflet Map Setup
    const map = L.map("map").setView([0, 0], 2); // Default to world view
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);
    let vehicleMarker = null; // Store marker reference

    // Register Vehicle Function
	vehicleForm.addEventListener("submit", async function (event) {
	    event.preventDefault();
	    const vehicleNumber = vehicleNumberInput.value.trim();

	    if (!vehicleNumber) {
	        alert("❌ Please enter a vehicle number.");
	        return;
	    }

	    try {
	        let response = await fetch("http://localhost:8080/vehicles/register", {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify({
	                vehicleNumber: vehicleNumber,
	                location: "Unknown Location" // Or whatever the default location should be
	            })
	        });

	        if (!response.ok) {
	            throw new Error("❌ Vehicle registration failed.");
	        }

	        let data = await response.json();

	        if (!data || !data.vehicleNumber) {
	            throw new Error("❌ Invalid response from server.");
	        }

	        alert(`✅ Vehicle Registered: ${data.vehicleNumber}`);

	        // Fetch vehicle location after registering
	        getVehicleLocation(data.vehicleNumber);
	    } catch (error) {
	        console.error("Error:", error);
	        alert(error.message);
	    }
	});


    // Fetch and Display Vehicle Location
    async function getVehicleLocation(vehicleNumber) {
        try {
            let response = await fetch(`http://localhost:8080/vehicles/track/${vehicleNumber}`);

            if (!response.ok) {
                throw new Error("❌ Vehicle tracking failed.");
            }

            let data = await response.json();

            if (!data || !data.vehicleNumber) {
                alert("❌ Vehicle not found!");
                return;
            }

            if (data.location === "Unknown Location") {
                alert("📍 Location not found for this vehicle.");
                return;
            }

            alert(`🚗 Vehicle Found: ${data.vehicleNumber}, Location: ${data.location}`);

            updateMap(data.location);
        } catch (error) {
            console.error("Error fetching location:", error);
            alert("❌ Failed to fetch vehicle location.");
        }
    }

    function updateMap(location) {
        if (!location || location === "Unknown Location") {
            alert("📍 Location data unavailable!");
            return;
        }

        let coordinates = parseLocation(location);

        // Remove previous marker if exists
        if (vehicleMarker) {
            map.removeLayer(vehicleMarker);
        }

        // Update map and marker
        map.setView(coordinates, 13);
        vehicleMarker = L.marker(coordinates)
            .addTo(map)
            .bindPopup("🚗 Vehicle is here!")
            .openPopup();
    }

    function parseLocation(location) {
        let parts = location.split(",");
        return [parseFloat(parts[0]), parseFloat(parts[1])];
    }
});
