
import { Injectable } from '@angular/core';
import { Message } from '../entities/message';
import { HttpClient } from '@angular/common/http';
import { StorageService } from './storage.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';

@Injectable({
    providedIn: 'root'
})

export class ChatService {
    private API_URL = 'http://localhost:8080/api';
    private HTTP_OPTIONS = {
      headers: {
          authorization: 'Bearer ' + this.localStorage.get('access_token')
      }
    };
    constructor(private http: HttpClient, private localStorage: StorageService) { }

    send(question: any) {
        return this.http.get<Message>(this.API_URL + "/nick/question?question=" + `${question}`, this.HTTP_OPTIONS);
    }
}