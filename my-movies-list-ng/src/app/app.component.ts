import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Optional } from '@angular/core';
import { Movie } from './movie';
import { MovieListService } from './movie-list.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public movies: Movie[];

  constructor(private movieService: MovieListService) { }

  ngOnInit() {
  }

  public getMovies(): void {
    this.movieService.getMovies().subscribe(
      (response: Movie[]) => {
        this.movies = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchMovies(): void {
    var movieName = (<HTMLInputElement>document.getElementById("search")).value;
    this.movieService.searchImdbMovies(movieName).subscribe(
      (response: void) => {
        console.log(response);
      }
    );
    this.movieService.searchMovies(movieName).subscribe(
      (response: Movie[]) => {
        this.movies = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public emptyMovies(): void {
    if (this.movies.length == 0) {
      var table = (<HTMLTableElement>document.getElementById("movie-table"));
      table.insertAdjacentHTML('afterbegin', "<div>Movie wasn't found<\div>");
    }
  }

  public filterMovies(): void {
    let filteredMovies: Movie[] = [];
    for (let i of this.movies) {
      if (i.favorite == true) {
        filteredMovies.push(i);
      }
    }
    this.movies = filteredMovies;
  }

  public checkStar(starId: bigint): void {
    var foundMovie: Movie;
    this.movieService.findMovie(starId).subscribe(
      (response: Movie) => {
        foundMovie = response;
        if (foundMovie.favorite == true) {
          foundMovie.favorite = false;
        } else {
          foundMovie.favorite = true;
        }
        this.movieService.updateMovie(foundMovie, starId).subscribe(
          (response: Movie) => {
            console.log(response);
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}

