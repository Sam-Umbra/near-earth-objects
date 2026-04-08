import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";

export interface OrbitRingConfig {
  radius: number;
  duration: number;
  dotSize: number;
  delay: number;
}

export interface HeroStat {
  label: string;
  value: string;
}

@Component({
  selector: 'app-hero-section',
  imports: [RouterLink],
  templateUrl: './hero-section.html',
  styleUrl: './hero-section.scss',
})

export class HeroSection {
  orbits: OrbitRingConfig[] = [
    { radius: 180, duration: 20, dotSize: 6, delay: 0 },
    { radius: 280, duration: 35, dotSize: 4, delay: 5 },
    { radius: 380, duration: 50, dotSize: 3, delay: 12 },
  ];

  stats: HeroStat[] = [
    { label: 'NEOs Tracked', value: 'Weekly' },
    { label: 'Latency', value: '<50ms' },
    { label: 'Uptime', value: '99.9%' },
  ];
}
