import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { UserService } from '../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { StorageService } from '../services/storage.service';
import { Router } from '@angular/router';
import { ChatService } from '../services/chat.service';
import { HeaderComponent } from '../header/header.component';
import { Message } from '../entities/message';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  messageContent: string = ''
  messages: Message[] = [
    { value: "Olá" },
    { value: "Olá, tudo bem?" },
    { value: "Horário da faculdade?" },
    { value: "A faculdade está aberta das 8 as 17 de segunda  sábado" }
  ];
  private notifier: NotifierService;
  
  formUser = this.formBuilder.group({
    question: ['', [Validators.required]]
  });

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private chatService: ChatService, 
    private formBuilder: FormBuilder,
    private storage: StorageService, notifier: NotifierService,
    private router: Router) { this.notifier = notifier; }
    
  ngOnInit(): void {
  }

  send(): void {
    try {
      if(!this.formUser.value.question)
        this.notifier.notify('error','Preencha todos os campos');

      this.chatService.send(this.messageContent).subscribe((res) => {
        this.messages.push(res)
      });
      
      this.messageContent = '';
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }
  clearMessages(): void {
    this.messageContent = '';
  }
}
