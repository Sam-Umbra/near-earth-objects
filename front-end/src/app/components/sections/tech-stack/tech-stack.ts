import { Component } from '@angular/core';
import { RevealDirective } from '../../../helpers/reveal.directive';

@Component({
  selector: 'app-tech-stack',
  imports: [RevealDirective],
  templateUrl: './tech-stack.html',
  styleUrl: './tech-stack.scss',
})
export class TechStack {
  stack = [
    { name: 'Python', role: 'ML & Risk Analysis', color: 'hsl(210, 100%, 50%)' },
    { name: 'FastAPI', role: 'Model & Java Bridge', color: 'hsl(170, 100%, 45%)' },
    { name: 'Java Spring', role: 'Real-time Processing', color: 'hsl(145, 80%, 50%)' },
    { name: 'WebSockets', role: 'Low-latency Feed', color: 'hsl(36, 100%, 50%)' },
    { name: 'NASA NeoWs', role: 'Orbital Data Source', color: 'hsl(0, 85%, 55%)' },
    { name: 'Angular', role: 'Radar Interface', color: 'hsl(350, 100%, 50%)' },
  ];
}
