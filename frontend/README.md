# Simple template to create SPA's with plain JavaScript using Babel, Webpack and Webpacks devserver

## Getting started

- If not alredy done, install nodejs and a sufficient JavaScript Editor (we suggest vs-code)
- Clone this project
- In the folder it was cloned into, type npm install
- In the folder it was cloned into (if you have installed vs-code) type "code ." to open vs-code
- In the folder it was cloned into type npm start, to run the project via Webpacks development server
- A browser window should now open rendering the project hosted on Webpacks dev server. You now have a local web server with hot reloading. Any changes made in your code are directly reflected in the browser (How cool is that?)

### Deploy to Nginx
If you have prepared Nginx for hosting this project, you can deploy it using the simple script 'deploy.sh' found in the root of the project
*Make sure* to set the values for XXXX and DROPLET_URL first

### Using Bootstrap that requires the Bootstrap JavaScrip
To minimize the footprint made by this code, it does not include the JavaScript part of BootStrap. If you need it, follow these steps:

- In the root of the project type: `npm install jquery popper.js`
- In your *index.js* file, add this import just ABOVE the line that includes the bootstrap.css: `import "bootstrap"`
