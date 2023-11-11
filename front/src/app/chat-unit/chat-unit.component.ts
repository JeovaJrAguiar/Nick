import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-chat-unit',
  templateUrl: './chat-unit.component.html',
  styleUrls: ['./chat-unit.component.css']
})
export class ChatUnitComponent implements OnInit {
  
  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor( private router: Router ) { }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
