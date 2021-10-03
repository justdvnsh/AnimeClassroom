<div id="top"></div>

[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/justdvnsh)
<a href="https://patreon.com/endel"><img src="https://img.shields.io/endpoint.svg?url=https%3A%2F%2Fshieldsio-patreon.vercel.app%2Fapi%3Fusername%3justdvnsh%26type%3Dpatrons&style=for-the-badge" /></a>



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="">
    <img src="./docs/app_icon.jpg" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">AnimeClassroom</h3>

  <p align="center">
    An awesome app to watch and download anime and read manga. No ads ! I promise.
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/justdvnsh/AnimeClassroom/issues">Report Bug</a>
    ·
    <a href="https://github.com/justdvnsh/AnimeClassroom/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

The project aims to solve a personal problem of watching anime. This app helps you watch and download anime without any ads. Plus it also lets you read and save mangas into the app itself. It parses the animes online from the web in real-time to provide you with a seamless watching experience. 

Why an anime app ? Here's why:
* Netflix contains anime's, but there is no denying that the list of anime's are very limited in Netflix.
* You can watch anime online, but you have to suffer with the ads.
* You can easily download the animes and also read manga in your phone itself. 

__WARNING: THIS PROJECT IS STILL IN HEAVY DEVELOPMENT, THEREFORE YOU MAY ENCOUNTER BUGS. You can OPEN the ISSUE on GITHUB REPOSITORY.__

__NEW ISSUES WILL BE ADDED REGULARLY__ 
  
__Do not PUT ANIMECLASSROOM OR ANY FORK OF IT INTO GOOGLE PLAYSTORE or Any other Store. It may VIOLATE THEIR TERMS AND CONDITIONS or you may encounter legal obligations.__

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

Simply fork the repo. Clone it into your machine and let AndroidStudio handle the rest.

<!-- ROADMAP -->
## Roadmap for Version 1 release of the app.

- [x] Add README
- [x] Setup a clean architecture for the app
- [x] Parse Animes
    - [x] Parse Anime Details
        - Create a new modern UI for the Anime Detail fragment
    - [x] Add Anime Search option
        - Handle the errors when anime not found
    - Handle the network error (When not connected to internet)
    - Handle cases when the unwanted errors occur
        - Could Not Parse Exception
        - Could Not Load Exception
    - Make the background function calls more efficient.
    - Need a better architecture for video player
        - handle audio focus change
        - add PIP mode for the player
        - handle speed changes
        - handle quality changes 
- [x] Parse Mangas
    - Setup the home page for mangas 
    - Parse Manga details
        - Create a modern UI for the Manga detail page
        - Setup viewpager based manga player to read manga like a book
    - Handle the network error (When not connected to internet)
    - Handle cases when the unwanted errors occur
        - Could Not Parse Exception
        - Could Not Load Exception
    - Need a better architecture for manga player
            - handle viewpager
            - handle night light support
            - handle quality changes 
- [x] Setup Favorites page
    - Make a viewpager to display all the saved animes in one tab, while all the saved mangas in the other.
    - setup room to save the details
    - make a modern motion layout based UI for the page.
- [x] Setup Settings page
    - Multi-language Support
    - dark mode toggle
    - downloaded videos
    - about
    - help
    - FAQ's 
- Setup a new module to provide glide dependencies
- Setup a new module to provide coroutines dependencies
    - load glide components in a background thread.
- A new modern color scheme for the app.

See the [open issues](https://github.com/justdvnsh/AnimeClassroom/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "feature-request".
Don't forget to give the project a star! Thanks again!

__NOTE -> Every PR will be reviewed before merging.__

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

__Want to support me by buying me a coffee ?__ [!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/justdvnsh)

__Want to contribute to this project by supporting us through money ?__ <a href="https://patreon.com/endel"><img src="https://img.shields.io/endpoint.svg?url=https%3A%2F%2Fshieldsio-patreon.vercel.app%2Fapi%3Fusername%3justdvnsh%26type%3Dpatrons&style=for-the-badge" /></a>

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Divyansh Dwivedi - [@justdvnsh](https://linkedin.com/in/justdvnsh) - justdvnsh2208@gmail.com

Project Link: [https://github.com/justdvnsh/AnimeClassroom](https://github.com/justdvnsh/AnimeClassroom)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/justdvnsh
[product-screenshot]: images/screenshot.png
