import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private readonly http: HttpClient) {
  }

  getData(): Observable<string[]> {
    return this.http.get<string[]>('/api/v1/data');
  }
}
