import { Component, OnInit } from '@angular/core';
import { latLng, tileLayer, icon, marker, map } from 'leaflet';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-maps-component',
  templateUrl: './maps-component.component.html',
  styleUrls: ['./maps-component.component.css']
})

export class MapsComponentComponent implements OnInit {

  waypoints:JSON

  constructor(private http: HttpClient) { }

  ngOnInit() {
    const myMap = map('map').setView([39.162911, -101.723353], 4);
    tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { attribution: '&copy; OpenStreetMap contributors' }).addTo(myMap);
    const markerIcon = icon({
      iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.2.0/images/marker-icon.png'
    });
    this.http.get('./assets/chapters.json', { responseType: 'json' }).subscribe(data => {
      this.waypoints = data['Waypoints'];
      for (var i = 0; i < this.waypoints.length; i++){
          var current_waypoint = this.waypoints[i];
          marker([current_waypoint.lat, current_waypoint.lng ], {icon: markerIcon}).bindPopup(current_waypoint.label).addTo(myMap);
      }
    });
  }
  /*
  options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      })
    ],
    zoom: 4,
    center: latLng([ 39.162911, -101.723353 ])
  };
  */
}
