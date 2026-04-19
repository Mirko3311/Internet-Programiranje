import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LeftSideBar } from '../left-side-bar/left-side-bar';
import { ActivatedRoute, NavigationEnd, Router, RouterOutlet } from '@angular/router';
import {filter} from 'rxjs';
import { MatMenuModule } from '@angular/material/menu';
@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, MatMenuModule, MatSidenavModule, MatSidenavModule, MatToolbarModule, LeftSideBar ,MatListModule, MatIconModule, RouterOutlet],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})

export class Dashboard implements OnInit
{
  activatePageTitle: string = 'Studentske prakse';
  constructor(private router: Router, private activateRoute: ActivatedRoute){}

  ngOnInit(){
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(()=>
    {
      this.updateTitle();
    });
      this.updateTitle();
  }


  private updateTitle() {
    let route = this.activateRoute.snapshot;
    // Idemo do najdublje "dijete" rute da uzmemo njen naslov
    while (route.firstChild) {
      route = route.firstChild;
    }
    this.activatePageTitle = route.data['title'] || 'Studentske prakse';
  }

  logout()
  {
    localStorage.removeItem('useToken');

    this.router.navigate(['/login']);
    console.log('Student se uspjesno odjavio');
  }
}
