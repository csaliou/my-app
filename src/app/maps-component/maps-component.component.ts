import { Component, OnInit } from '@angular/core';
import { latLng, tileLayer } from 'leaflet';

@Component({
  selector: 'app-maps-component',
  templateUrl: './maps-component.component.html',
  styleUrls: ['./maps-component.component.css']
})
export class MapsComponentComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      })
    ],
    zoom: 5,
    center: latLng([ 46.879966, -121.726909 ])
  };

}
