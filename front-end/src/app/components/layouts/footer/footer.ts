import { Component, input } from '@angular/core';

@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
})
export class Footer {
  githubUrl = input<string>('https://github.com/Sam-Umbra/space-radar');
  nasaApiUrl = input<string>('https://api.nasa.gov/');
}
