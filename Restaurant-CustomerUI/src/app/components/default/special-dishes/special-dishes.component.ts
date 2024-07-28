import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { EncryptDecryptService } from 'src/app/services/common/encrypt-decrypt.service';
import { UserService } from 'src/app/services/user-coupon-order/user.service';
import { ProductService } from 'src/app/services/product-review/product.service';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { product } from 'src/app/models/product';
import { secretKey } from 'src/app/models/secretKey';
import { foodType } from 'src/app/models/foodType';
import { SnackbarService } from 'src/app/services/common/snackbar.service';
import { CartItemsInfo } from 'src/app/models/cartItemsInfo';

@Component({
  selector: 'app-special-dishes',
  templateUrl: './special-dishes.component.html',
  styleUrls: ['./special-dishes.component.css'],
})
export class SpecialDishesComponent implements OnInit {
  pageNo: number = 1;
  productPerPage: number = 3;
  isLoggedIn: boolean = false;
  isLoaded: boolean = false;
  @Input() homeRouteFlag: boolean;
  specialDishes: product[] = [];
  constructor(
    private productService: ProductService,
    private snackbarSerivce: SnackbarService,
    private authService: AuthService,
    private userService: UserService,
    private encrypt_decrypt: EncryptDecryptService,
    private loader: ScreenLoaderService
  ) {}

  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.authService.isLoggedIn.subscribe((data) => {
      this.isLoggedIn = data;
    });
    this.getAllSpecialDishes();
  }

  getAllSpecialDishes() {
    this.productService
      .getProductByFoodType(foodType.SPECIAL_DISH)
      .subscribe((data) => {
        this.specialDishes = data.body;
      });
  }

  addToCart(dish: product) {
    let cart: CartItemsInfo[] = [];
    let data = sessionStorage.getItem(
      this.encrypt_decrypt.encryption('Cart', secretKey)
    );
    if (data !== null && data !== undefined) {
      cart = JSON.parse(this.encrypt_decrypt.decryption(data, secretKey));
    }
    for (let x of cart) {
      if (dish.pid === x.pid) {
        this.snackbarSerivce.info('Product already exists in your cart.', '');
        return;
      }
    }
    cart.push({ pid: dish.pid, quantity: dish.quantity });
    sessionStorage.setItem(
      this.encrypt_decrypt.encryption('Cart', secretKey),
      this.encrypt_decrypt.encryption(JSON.stringify(cart), secretKey)
    );
    if (this.isLoggedIn) {
      const currUser = this.userService.getCurrentUserDetails();
      this.userService
        .addToCartProducts(currUser.userId, cart)
        .subscribe((data) => {
          this.userService.setUserDetails(data.body);
          this.snackbarSerivce.success('Product added to your cart.', '');
        });
    }
  }
}
