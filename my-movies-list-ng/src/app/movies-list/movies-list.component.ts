import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Movie } from '../movie';
import { MovieListService } from '../movie-list.service';
import { Output, EventEmitter } from '@angular/core';
import { HomeComponent } from '../home/home.component';

@Component({
  selector: 'app-movies-list',
  templateUrl: './movies-list.component.html',
  styleUrls: ['./movies-list.component.css']
})
export class MoviesListComponent implements OnInit, OnChanges {

  @Input() searchList :Movie[];
  public movies : Movie[];


  ngOnChanges(changes: SimpleChanges){
  }


  constructor(private movieService : MovieListService, private homeComponent: HomeComponent) { }

  ngOnInit(): void {
    this.getMovies();
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
        this.movieService.updateMovie(foundMovie).subscribe(
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
