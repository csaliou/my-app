import { Component, OnInit,OnDestroy,EventEmitter,Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';


import { UnServiceService } from '../un-service.service';
import { Subscription } from 'rxjs/Subscription';


@Component({
  selector: 'app-chapters',
  templateUrl: './chapters.component.html',
  styleUrls: ['./chapters.component.css']
})

export class ChaptersComponent implements OnInit,OnDestroy {

  chapters:JSON // les chapitres
  @Output() onChapter = new EventEmitter<number>()
  messages: Subscription;

  constructor(private http: HttpClient,private service:UnServiceService) { }

  ngOnInit() {
      this.http.get('./assets/chapters.json', { responseType: 'json' }).subscribe(data => {
        this.chapters = data['Chapters'];
    });
    this.messages = this.service.messages.subscribe(
      message => {
        //on consomme le message
      });
  }

  ngOnDestroy(){
   this.messages.unsubscribe()
 }

  onSelect(chapter){
    this.onChapter.emit(chapter.pos);
  }
}
