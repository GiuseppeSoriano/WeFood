import { Component, OnInit } from '@angular/core';
import { Admin, AdminInterface } from '../models/admin.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  info: AdminInterface = new Admin();

  constructor(private router:Router) {
    const navigation = this.router.getCurrentNavigation();
    if(navigation){
      const state = navigation.extras.state as {
        Admin: AdminInterface
      };
      this.info = state.Admin;
    }
  }

  ngOnInit(): void {
  }

}
