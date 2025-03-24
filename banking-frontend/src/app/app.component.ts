import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {CommonModule} from '@angular/common';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, InputTextModule, ButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'banking-frontend';
}
