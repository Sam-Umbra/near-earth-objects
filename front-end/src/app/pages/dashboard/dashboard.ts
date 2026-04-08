import { Component } from '@angular/core';
import { StarField } from "../../components/star-field/star-field";
import { Header } from "../../components/layouts/header/header";
import { Main } from "../../components/layouts/main/main";
import { Footer } from "../../components/layouts/footer/footer";

@Component({
  selector: 'app-dashboard',
  imports: [StarField, Header, Main, Footer],
  templateUrl: './dashboard.html',
})
export class Dashboard {}
