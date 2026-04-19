import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";


@Injectable({ providedIn:'root'})
export class AuthService
{
    private apiUrl = `${environment.apiUrl}/auth`;

    constructor(private http: HttpClient) {}

    login(loginData: any): Observable<any>
    {
      return this.http.post(`${this.apiUrl}/login`,loginData,
    {
        withCredentials:true
    })
      .pipe(tap((response: any) => 
        {
            if(response)
            {
                localStorage.setItem('currentUser', JSON.stringify(response));
                localStorage.setItem('isLoggedIn', 'true');
            }
        }));
    }

    logout()
    {
        return this.http.post(`${this.apiUrl}/logout`,{},
            {withCredentials:true}).subscribe
        ({
            next:() =>
            {
                localStorage.clear();
                window.location.reload();
            }
        });
    }

    isLoggedIn():boolean
    {
        return !!localStorage.getItem('token');
    }
}