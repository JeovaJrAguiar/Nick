import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { User } from '../entities/user';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private API_URL = 'http://localhost:8080/api/user';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.localStorage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private localStorage: StorageService, private router: Router) { }

  login(username: any, password: any): Observable<any>{
    this.localStorage.remove('acsess_token');
    this.localStorage.remove('user_id');
    const body = new FormData();
    body.append('enrollment', username);
    body.append('password', password);

    return this.http.post<any>(this.API_URL+ "/login", body)
      .pipe(
        tap(res => {
          this.localStorage.set('access_token', res.token);
          this.localStorage.set('user_id', res.user_id);
        })
      );
  }

  getUserById(id: any) {
    return this.http.get<User>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }

  addUser(user: any) {
    return this.http.post<any>(this.API_URL, user); 
  }

  updateUser(user: any, id: any) {
    return this.http.put<any>(this.API_URL+`/${id}`, user, this.HTTP_OPTIONS); 
  }

  deleteUser(id: any) {
    return this.http.delete<any>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }

  logout(): void{
    this.localStorage.remove('acsess_token');
    this.localStorage.remove('user_id');
    this.router.navigate(['/']);
  }
}
