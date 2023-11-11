import { Component } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { UserService } from '../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { StorageService } from '../services/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  private notifier: NotifierService;
  
  formUser = this.formBuilder.group({
    enrollment: ['', [Validators.required]],
    name: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  data: any;

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
  }

  register(): void{
    try {
      if(!this.formUser.value.name || !this.formUser.value.enrollment || !this.formUser.value.password)
        this.notifier.notify('error','Preencha todos os campos obrigatórios');

      this.data = this.formUser.value;
      this.registerService.addUser(this.data).subscribe(() => {
        this.storage.logoutUser();
        this.router.navigate(['/']);
      }, () => {
        this.storage.logoutUser();
        this.notifier.notify('error', 'Não foi possível realizar o cadastro no momento, tente novamente mais tarde');
      });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  redirect(): void{
    this.router.navigate(['/']);
  }
}
