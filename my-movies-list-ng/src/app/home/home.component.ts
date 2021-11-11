import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Movie } from '../movie';
import { MovieListService } from '../movie-list.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  searchInput : string = "";
  searchList : Movie[];

  public movies: Movie[];

  constructor(private movieService: MovieListService) { }

  ngOnInit(): void {
  }

  public searchMovies(): void {
    this.movieService.searchImdbMovies(this.searchInput).subscribe(
      (response: void) => {
        console.log(response);
        this.movieService.searchMovies(this.searchInput).subscribe(
          (response: Movie[]) => {
            this.searchList = response;
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      }
    );

  }
}
