import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from './movie';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieListService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.apiServerUrl}/movie/all`)
  }

  public addMovie(movie: Movie): Observable<Movie> {
    return this.http.post<Movie>(`${this.apiServerUrl}/movie/add`, movie)
  }

  public updateMovie(movie: Movie, movieId: bigint): Observable<Movie> {
    return this.http.put<Movie>(`${this.apiServerUrl}/movie/update/${movieId}`, movie)
  }

  public deleteMovie(movieId: bigint): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/movie/delete/${movieId}`)
  }

  public findMovie(movieId: bigint): Observable<Movie> {
    return this.http.get<Movie>(`${this.apiServerUrl}/movie/find/${movieId}`)
  }

  public searchImdbMovies(movieName: String): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/movie/imdb/${movieName}`, null)
  }

  public searchMovies(movieName: String): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.apiServerUrl}/movie/find_name/${movieName}`)
  }
}
