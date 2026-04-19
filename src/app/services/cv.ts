import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root',})
export class CvService 
{
  private readonly API_URL = `${environment.apiUrl}/cv`;

  constructor(private http:HttpClient)
  {}
  
  create(cvData:any):Observable<any>
  {
    return this.http.post(`${this.API_URL}/create`,cvData,
      {
        withCredentials:true
      }
    );
  }

  addSkills(cvId:number, skills:string[]):Observable<any>
  {
    return this.http.post(`${this.API_URL}/cv/${cvId}/skills`,skills,
      {
        withCredentials:true
      }
    );
  }

  deleteCV():Observable<any>
  {
    return this.http.delete(`${this.API_URL}/delete`,
      {
        withCredentials:true
      });
  }


  update(data:any)
  {
    return this.http.put<any>(`${this.API_URL}/update-my-cv`, data,
      {
        withCredentials:true
      }
    );
  }

  getMojCV():Observable<any>
  {
    return this.http.get<any>(`${this.API_URL}/moj-cv`,
    {
      withCredentials:true
    });
  }
}
