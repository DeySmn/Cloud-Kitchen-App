import { Component } from '@angular/core';
import { FeedbackService } from 'src/app/services/feedback/feedback.service';
import { feedback } from '../../../models/feedback';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css'],
})
export class ReviewComponent {
  customerTestimony: feedback[] = [];
  isLoaded: boolean = false;
  constructor(
    private feedbackService: FeedbackService,
    private loader: ScreenLoaderService
  ) {}

  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.customerTestimony = JSON.parse(sessionStorage.getItem('feedback')??'[]');
    if (null === this.customerTestimony || this.customerTestimony.length === 0) {
      this.feedbackService.getCustomerTestimony().subscribe((data) => {
        this.customerTestimony = data;
        console.log(this.customerTestimony);
        sessionStorage.setItem('feedback', JSON.stringify(this.customerTestimony));
      });
    }
  }
}
