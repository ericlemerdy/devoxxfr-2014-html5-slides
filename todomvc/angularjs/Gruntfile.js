var execSync = require('execSync');
module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        packageName: "app-<%= pkg.version %>.tar.gz",
        sftp: {
            deploy: {
                files: {
                    './': "<%= packageName %>"
                },
                options: {
                    path: '/var/www',
                    createDirectories: true
                }
            }
        },
        sshexec: {
            extract: {
                command: "tar xzf /var/www/<%= packageName %> -C /var/www && rm /var/www/<%= packageName %>"
            }
        }
    });

    grunt.loadNpmTasks('grunt-ssh');

    grunt.registerTask('package', function() {
        var packageName = grunt.config('packageName');
        execSync.exec('tar czf ' + packageName + ' index.html js bower_components package.json');
    });

    grunt.registerTask('deploy', [
        'sftp:deploy',
        'sshexec:extract'
    ]);

    grunt.registerTask('default', [
        'package',
        'deploy'
    ]);
};
