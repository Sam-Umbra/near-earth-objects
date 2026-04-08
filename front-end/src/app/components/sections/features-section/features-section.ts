import { AfterViewInit, Component, ElementRef, inject, viewChildren } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

export interface Feature {
  icon: SafeHtml;
  title: string;
  description: string;
  color: 'primary' | 'accent';
}

@Component({
  selector: 'app-features-section',
  imports: [],
  templateUrl: './features-section.html',
  styleUrl: './features-section.scss',
})
export class FeaturesSection implements AfterViewInit {
  private sanitizer = inject(DomSanitizer);
  private s = (svg: string): SafeHtml => this.sanitizer.bypassSecurityTrustHtml(svg);

  features: Feature[] = [
    {
      icon: this.s(
        `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M13 7 9 3 5 7l4 4"/><path d="m17 11 4 4-4 4-4-4"/><path d="m14 6-8.5 8.5a2.12 2.12 0 1 0 3 3L17 9"/></svg>`,
      ),
      title: 'NASA NeoWs Integration',
      description:
        "Direct feed from NASA's Near Earth Object Web Service. Asteroid orbital data, diameter estimates, and close-approach parameters updated in real-time.",
      color: 'primary',
    },
    {
      icon: this.s(
        `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 5a3 3 0 1 0-5.997.125 4 4 0 0 0-2.526 5.77 4 4 0 0 0 .556 6.588A4 4 0 1 0 12 18Z"/><path d="M12 5a3 3 0 1 1 5.997.125 4 4 0 0 1 2.526 5.77 4 4 0 0 1-.556 6.588A4 4 0 1 1 12 18Z"/><path d="M15 13a4.5 4.5 0 0 1-3-4 4.5 4.5 0 0 1-3 4"/><path d="M17.599 6.5a3 3 0 0 0 .399-1.375"/><path d="M6.003 5.125A3 3 0 0 0 6.401 6.5"/><path d="M3.477 10.896a4 4 0 0 1 .585-.396"/><path d="M19.938 10.5a4 4 0 0 1 .585.396"/><path d="M6 18a4 4 0 0 1-1.967-.516"/><path d="M19.967 17.484A4 4 0 0 1 18 18"/></svg>`,
      ),
      title: 'ML Risk Classification',
      description:
        'Python/FastAPI pipeline running a trained classifier that scores collision probability using velocity, miss distance, and diameter features.',
      color: 'accent',
    },
    {
      icon: this.s(
        `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4.9 19.1C1 15.2 1 8.8 4.9 4.9"/><path d="M7.8 16.2c-2.3-2.3-2.3-6.1 0-8.5"/><circle cx="12" cy="12" r="2"/><path d="M16.2 7.8c2.3 2.3 2.3 6.1 0 8.5"/><path d="M19.1 4.9C23 8.8 23 15.1 19.1 19"/></svg>`,
      ),
      title: 'WebSocket Broadcasts',
      description:
        'Zero-polling architecture. Connected clients receive instant threat-level updates the moment new NEO data is processed and classified.',
      color: 'primary',
    },
    {
      icon: this.s(
        `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m7 11 2-2-2-2"/><path d="M11 13h4"/><rect width="18" height="18" x="3" y="3" rx="2" ry="2"/></svg>`,
      ),
      title: 'Java & Spring Boot',
      description:
        'Backend powered by Java with Spring Boot, handling WebSocket connections, REST endpoints, and business logic with production-grade resilience.',
      color: 'accent',
    },
    {
      icon: this.s(
        `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 13c0 5-3.5 7.5-7.66 8.95a1 1 0 0 1-.67-.01C7.5 20.5 4 18 4 13V6a1 1 0 0 1 1-1c2 0 4.5-1.2 6.24-2.72a1.17 1.17 0 0 1 1.52 0C14.51 3.81 17 5 19 5a1 1 0 0 1 1 1z"/><path d="M12 8v4"/><path d="M12 16h.01"/></svg>`,
      ),
      title: 'Three-Level Threat System',
      description:
        'Streamlined Safe / Caution / Danger classification. Each level triggers appropriate UI alerts and real-time notification broadcasts to connected clients.',
      color: 'primary',
    },
    {
      icon: this.s(
        `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m12 14 4-4"/><path d="M3.34 19a10 10 0 1 1 17.32 0"/></svg>`,
      ),
      title: 'Real-Time Dashboard',
      description:
        'Mission-control interface displaying active NEOs, risk distributions, approach timelines, and system health metrics at a glance.',
      color: 'accent',
    },
  ];

  cardRefs = viewChildren<ElementRef>('featureCard');

  ngAfterViewInit(): void {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add('features_card--visible');
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.15 },
    );

    this.cardRefs().forEach((ref) => observer.observe(ref.nativeElement));
  }
}
