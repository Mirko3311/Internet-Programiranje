import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LeftSideBar } from './components/left-side-bar/left-side-bar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend-student-studentske-prakse');
}
