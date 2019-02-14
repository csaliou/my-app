import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable({
  providedIn: 'root'
})
export class UnServiceService {
  private messagesSource = new Subject<string>();
  messages = this.messagesSource.asObservable();

  constructor() { }
  
  post(message:string){
  this.messagesSource.next(message)
  }

}
