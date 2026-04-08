import { ChangeDetectionStrategy, Component} from '@angular/core';
import { Header } from "./components/layouts/header/header";
import { Main } from "./components/layouts/main/main";
import { StarField } from "./components/star-field/star-field";

@Component({
  selector: 'app-root',
  imports: [Header, Main, StarField],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  
}
