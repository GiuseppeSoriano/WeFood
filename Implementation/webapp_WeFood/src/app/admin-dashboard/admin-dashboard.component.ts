import { Component, OnInit } from '@angular/core';
import { Admin, AdminInterface } from '../models/admin.model';
import { Router } from '@angular/router';
import { AdminService } from '../services/admin_service/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  // info: AdminInterface = new Admin();
  logout() {
    this.adminService.logout();
    this.router.navigate(['/home']);
  }

  constructor(private router:Router, private adminService: AdminService) {
    const navigation = this.router.getCurrentNavigation();
    // if(navigation){
    //   const state = navigation.extras.state as {
    //     Admin: AdminInterface
    //   };
    //   this.info = state.Admin;
    // }
  }

  ngOnInit(): void {
    if(this.adminService.info.username == ""){
      this.router.navigate(['/home']);
    }
  }

}
