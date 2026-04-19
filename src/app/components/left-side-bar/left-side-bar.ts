import { Component } from '@angular/core';
import { MatNavList,MatListItem, MatListItemIcon } from '@angular/material/list';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-left-side-bar',
  standalone:true,
  imports: [MatNavList, MatListItem,MatListItemIcon,MatIconModule,RouterLink,RouterLinkActive],
  templateUrl: './left-side-bar.html',
  styleUrl: './left-side-bar.css',
})
export class LeftSideBar 
{
 items = [
  {
    routeLink: 'cv',
    icon: 'description',
    label: "CV"
  },
  {
    routeLink: 'prakse',
    icon: 'work',
    label: 'Prakse'
  },
  {
    routeLink:'preporuke',
    icon:'auto_awesome',
    label: 'AI Preporuke'
  },
  {
    routeLink: 'dnevnik',
    icon: "assignment",
    label: 'Dnevnik rada'
  }];
}
