import { foodType } from './foodType';

export class ResponseEntity {
  body: any;
  status: string;
  constructor(
    $body: any,
    $status: string
  ) {
    this.body = $body;
    this.status = $status;
  }
}
