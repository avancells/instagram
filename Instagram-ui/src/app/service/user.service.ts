import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs/';
import { catchError, tap, map } from 'rxjs/operators';

import { CONST } from '../const/const';
import { handleError } from '../error-handle/error.handling';
import { User } from '../model/User';
import { authService } from './auth.service';
import { PassDto } from '../model/passDto';

@Injectable()
export class UserService {


  constructor(private httpClient: HttpClient, private authenticationService: authService) { }

  getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(CONST.URL_USERS_GET_ALL)
      .pipe(
        tap(users => console.log(`fetched users`)),
        catchError(handleError('getUsers', []))
      );
  }

  registerUser(user: User) {
    return this.httpClient.post<User>(CONST.URL_USERS_ADD, user)
      .pipe(
        tap(p => console.log(`registered user`))
      );
  }

  changeUserData(user:User){
    return this.httpClient.put<User>(CONST.URL_PUT_UPDATE_ALL,user)
      .pipe(
        tap(p=> console.log('user data has been change'))
      )
  }

  getProfile(name: String): Observable<User> {
    return this.httpClient.get<User>(CONST.URL_USER_GET_BY_USERNAME + name);
  }

  getAmountFollowers(id: number): Observable<number> {
    return this.httpClient.get(CONST.URL_FOLLOWERS + id).pipe(map((users: User[]) => users.length));
  }

  getAmountFolloweds(id: number): Observable<number> {
    return this.httpClient.get(CONST.URL_FOLLOWEDS + id).pipe(map((users: User[]) => users.length));
  }

  getFollowers(id: number): Observable<User[]> {
    return this.httpClient.get(CONST.URL_FOLLOWERS + id).pipe(map((users: User[]) => users));
  }

  getFolloweds(id: number): Observable<User[]> {
    return this.httpClient.get(CONST.URL_FOLLOWEDS + id).pipe(map((users: User[]) => users));
  }

  getAmountPost(id: number): Observable<number> {
    return this.httpClient.get(CONST.URL_POST + id).pipe(map((posts: any[]) => posts.length));
  }

  modifyPass(oldpass: string, userName: string, newPass: string): Observable<boolean> {
    var passDto = new PassDto();
    passDto.username = userName;
    passDto.newPassword = newPass;
    passDto.oldPassword = oldpass;

    const URL = CONST.URL_PUT_USER_PASSWORD
    return this.httpClient.put<boolean>(URL,passDto)
      .pipe(
        tap(users => console.log(`changed password`)),
        catchError(handleError('getUsers',false)
      );
  }



}
