import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  formUser = this.formBuilder.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

      /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
      constructor(private loginService: UserService, 
        private formBuilder: FormBuilder,
        private storage: StorageService, notifier: NotifierService,
        private router: Router) { this.notifier = notifier; }
  
    ngOnInit(): void {
    }

    login() {
      try {
        if(!this.formUser.value.email || !this.formUser.value.password)
          this.notifier.notify('error','Preencha todos os campos');
  
          this.loginService.login(this.formUser.value.email, this.formUser.value.password).subscribe((user) => {
          this.router.navigate(['/home']);
          }, (error) => {
            this.notifier.notify('error', 'Informações inválidas');
          });
      }
      catch (ex: any) {
        this.notifier.notify('error', ex);
      }
    }
  
    redirect(): void{
      this.router.navigate(['/register']);
    }
}
