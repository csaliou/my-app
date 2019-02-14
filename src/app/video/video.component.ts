import { Component, OnInit,OnDestroy } from '@angular/core';
import { UnServiceService } from '../un-service.service';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css']
})

export class VideoComponent implements OnInit,OnDestroy {

  url_mp4 = "https://archive.org/download/Route_66_-_an_American_badDream/Route_66_-_an_American_badDream_512kb.mp4"
  url_ogg = "https://archive.org/download/Route_66_-_an_American_badDream/Route_66_-_an_American_badDream.ogv"
  time = 0

  messages: Subscription;
  constructor(private service:UnServiceService) { }

  ngOnInit() {
    this.messages = this.service.messages.subscribe(
      message => {
        //on consomme le message
      });
}
   ngOnDestroy(){
    this.messages.unsubscribe()
  }

  onChapterChange(pos:number){
    this.time = pos
  }

}
