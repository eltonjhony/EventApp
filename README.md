# Events App
- Event App that uses Ticketmaster API to present events all over the Germany.

### Features

- Present a list of events on a page (Load data using Ticketmaster API);
- When the user reaches the end of the list, the app has to automatically load more events (Endlees Scroll);
- Each event presents an image, name, dates, location and an icon to show if the event is a user favorite (on the wish list) or not;
- Present details of a specific event on a separate page, with all previous data and some other relevant information, such as Location GPS and a new sort of images;
- Wish list feature that allows users to favorite events and also list all favorites from my wish list page;
- Filter the events by genre and also search previous listings events by name using the search icon on the appBar;
- Compatible with all Android versions above 21 SDK API;

### How to run the Project (Setup)

- Download Android studio, clone the repository and run the project. There is no dependency to run the app. You only need the Android studio to automatically download the app dependencies and you will be able to run the app.

##### To make the setup simpler, I decided to leave the API key static on the Constants class. This is the public api key provided by the API documentation.

    interface Configs {
        companion object {
            const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
            const val API_KEY_PARAM = "apikey"
            const val API_KEY_VALUE = "CmqFKledVWHtMyAq85u6zk5KZUZmq8cw"
        }
    }
	
### Images

Screens:

<img width="402" alt="Screen Shot 2021-03-04 at 15 24 02" src="https://user-images.githubusercontent.com/25302517/110011085-cf372580-7cfd-11eb-83f9-61f3c7277b69.png">
> List Events screen


<img width="402" alt="Screen Shot 2021-03-04 at 15 24 15" src="https://user-images.githubusercontent.com/25302517/110011102-d2321600-7cfd-11eb-8fcf-9c81cf0ddf0a.png">
> Wish list screen with two events


<img width="403" alt="Screen Shot 2021-03-04 at 15 24 27" src="https://user-images.githubusercontent.com/25302517/110011104-d2caac80-7cfd-11eb-92f5-8a75ae190c29.png">
> Details screen showing a specific event


<img width="401" alt="Screen Shot 2021-03-04 at 15 24 40" src="https://user-images.githubusercontent.com/25302517/110011108-d3634300-7cfd-11eb-93dd-dd4fea792e6d.png">
> List Events screen with genre filter opened


<img width="406" alt="Screen Shot 2021-03-04 at 15 24 50" src="https://user-images.githubusercontent.com/25302517/110011112-d3fbd980-7cfd-11eb-97e5-95ae7b881cb9.png">
> List Events screen with search bar opened