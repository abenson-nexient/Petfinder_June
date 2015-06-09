'use strict';

module.exports = function(grunt) {

// Initial configuration
// ========================================================
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    connect: {
      server: { // task name
        options: {
          port: '8000',
          protocol: 'http',
          hostname: 'localhost',
          keepalive: true,
          livereload: true,
          open: true
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.registerTask('default', ['connect']);
};