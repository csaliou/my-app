import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css']
})

export class VideoComponent implements OnInit {

  url_mp4 = "https://archive.org/download/Route_66_-_an_American_badDream/Route_66_-_an_American_badDream_512kb.mp4"
  url_ogg = "https://archive.org/download/Route_66_-_an_American_badDream/Route_66_-_an_American_badDream.ogv"
  time = 0

  constructor() { }

  ngOnInit() { }

  onChapterChange(pos:number){
    this.time = pos
  }

}
