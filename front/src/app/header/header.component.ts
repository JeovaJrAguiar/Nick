import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { FormBuilder } from '@angular/forms';
import { UserService } from '../services/user.service';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  private notifier: NotifierService;
  
  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private registerService: UserService, 
    private formBuilder: FormBuilder,
    private storage: StorageService, notifier: NotifierService,
    private router: Router) { this.notifier = notifier; }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  
}
