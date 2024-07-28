import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { foodType } from 'src/app/models/foodType';
import { product } from 'src/app/models/product';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { ProductService } from 'src/app/services/product-review/product.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit {
  checked: boolean = false;
  isLoaded: boolean = false;
  foodType: string = '';
  allProducts: product[] = [];
  constructor(private router: Router, private route: ActivatedRoute, private productService: ProductService, private loader: ScreenLoaderService) { }
  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.route.params.subscribe((val: Params) => {
      this.foodType = val['foodtype'];
      this.getProductByFoodType(this.foodType.toUpperCase());
      this.foodType = (this.foodType == 'special_dish') ?
        'Special Dishes' : this.foodType;
    });

  }

  liveStatusChange(product: product) {
    //product.live = !product.live;
    this.productService.changeLiveStatus(product.pid).subscribe((data) => {
      this.getProductByFoodType(product.type);
    });
  }

  getProductByFoodType(type: string) {
    this.productService.getProductsByFoodType(type).subscribe((products: product[]) => {
      this.allProducts = products;
    });
  }
}
