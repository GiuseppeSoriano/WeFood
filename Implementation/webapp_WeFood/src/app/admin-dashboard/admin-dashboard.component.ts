// admin-dashboard.component.ts
import { Component, ElementRef, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { AdminService } from '../services/admin_service/admin.service';
import { PostService } from '../services/post_service/post.service';
import { IngredientService } from '../services/ingredient_service/ingredient.service';
import { PostDTOInterface } from '../models/post-dto.model';
import { Router } from '@angular/router';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';

// Importa Chart.js
import { Chart } from 'chart.js/auto';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  isLoading = false;
  list_of_posts: PostDTOInterface[] = [];
  //interactions: Map<string, number> = new Map<string, number>();
  filteredPosts: PostDTOInterface[] = [];
  hours_var: number = 87600;
  limit_var: number = 30;
  top_ingredients: string[] = [];
  least_ingredients: string[] = [];
  newIngredient: { name: string, calories: number } = { name: '', calories: 0 };
  interactionValues: any = {};
  interactionKeys: string[] = [];
  chart: any;
  ingredientCreation: boolean = false;

  // info: AdminInterface = new Admin();
  logout() {
    this.adminService.logout();
    this.router.navigate(['/home']);
  }

  constructor(
    private router: Router,
    private adminService: AdminService,
    private postService: PostService,
    private ingredientService: IngredientService,
    private userService: RegisteredUserService
  ) {}

  ngOnInit(): void {
    if (this.adminService.info.username == "") {
      this.router.navigate(['/home']);
    }
    this.getTopIngredients();
    this.getLeastIngredients();
    this.getStatistics();
  }

  getLeastIngredients() {
    this.ingredientService.findMostLeastUsedIngredients(true).subscribe(
      data => {
        this.top_ingredients = data;
      },
      error => {
        alert('Error in loading page');
      }
    );
  }

  getTopIngredients() {
    this.ingredientService.findMostLeastUsedIngredients(false).subscribe(
      data => {
        this.least_ingredients = data;
      },
      error => {
        alert('Error in loading page');
      }
    );
  }

  getStatistics(): void {
    this.postService.interactionsAnalysis().subscribe(
      data => {
        data = new Map(Object.entries(data));
        // console.log(data);
        this.interactionValues = {
          "avgOfStarRanking": {
            "IMAGE": data.get("IMAGEavgOfAvgStarRanking"),
            "NOIMAGE": data.get("NOIMAGEavgOfAvgStarRanking")
          },
          "ratioOfComments": {
            "IMAGE": data.get("IMAGEratioOfComments"),
            "NOIMAGE": data.get("NOIMAGEratioOfComments")
          },
          "ratioOfStarRankings": {
            "IMAGE": data.get("IMAGEratioOfStarRankings"),
            "NOIMAGE": data.get("NOIMAGEratioOfStarRankings"),
          }
        };
        this.interactionKeys = Object.keys(this.interactionValues);
        // console.log(this.interactionValues);
        this.createChart();
      },
      error => {
        alert('Error in loading page');
      }
    );
  }

  @ViewChild('myChart') chartRef: ElementRef | undefined;

  createChart(): void {
      if (this.chartRef) {
        const ctx = this.chartRef.nativeElement.getContext('2d');
        if (ctx.canvas) {
          this.chart = new Chart(ctx, {
          type: 'bar',
          data: {
            labels: this.interactionKeys,
            datasets: [
              {
                label: 'IMAGE',
                backgroundColor: 'rgba(157, 2, 8, 0.7)',
                borderColor: 'rgba(157, 2, 8, 1)',
                borderWidth: 1,
                data: this.interactionKeys.map(key => this.interactionValues[key]['IMAGE'])
              },
              {
                label: 'NOIMAGE',
                backgroundColor: 'rgba(250, 163, 7, 0.7)',
                borderColor: 'rgba(250, 163, 7, 1)',
                borderWidth: 1,
                data: this.interactionKeys.map(key => this.interactionValues[key]['NOIMAGE'])
              }
            ]
          },
          options: {
            scales: {
              x: {
                ticks: {
                  color: 'rgb(55, 6, 23)', // Change the font color of the x-axis labels
                  font: {
                    weight: 'bold' // You can also set other font properties here
                  }
                }
              },
              y: {
                beginAtZero: true,
                ticks: {
                  color: 'rgb(55, 6, 23)', // Change the font color of the y-axis labels
                  font: {
                    weight: 'bold' // You can also set other font properties here
                  }
                }
              }              
            },
            plugins: {
              legend: {
                labels: {
                  color: 'rgb(55, 6, 23)', // Change the font color of the legend labels
                }
              }
            }
          }
        });
      }
    }
  }


  createIngredient(): void {
    if (this.newIngredient.name && this.newIngredient.calories > 0) {
      alert(`Ingrediente aggiunto:\nNome: ${this.newIngredient.name}\nCalorie: ${this.newIngredient.calories}`);
      this.newIngredient = { name: '', calories: 0 };
    } else {
      alert('Invalid input.');
    }
  }


  openIngredientCreation() {
    this.ingredientCreation = true;
  } 
  closeIngredientCreation() {
    this.ingredientCreation = false;
  }

  goToAdminFeed() {
    this.router.navigate(['/admin-feed']);
  }

  /*
  interactionValues: any = {
    "avgOfStarRanking": {
      "IMAGE": this.interactions.get("IMAGEAvgOfStarRanking"),
      "NOIMAGE": this.interactions.get("NOIMAGEAvgOfStarRanking")
    },
    "ratioOfComments": {
      "IMAGE": this.interactions.get("IMAGEratioOfComments"),
      "NOIMAGE": this.interactions.get("NOIMAGEratioOfComments")
    },
    "ratioOfStarRankings": {
      "IMAGE": this.interactions.get("IMAGEratioOfStarRankings"),
      "NOIMAGE": this.interactions.get("NOIMAGEratioOfStarRankings"),
    }
  };
  
  interactionKeys = ["avgOfStarRanking", "ratioOfComments", "ratioOfStarRankings"];
  */
}
