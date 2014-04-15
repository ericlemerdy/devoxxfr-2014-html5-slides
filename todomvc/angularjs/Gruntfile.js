var execSync = require('execSync');
module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        packageName: "app-<%= pkg.version %>.tar.gz",
        sftp: {
            deploy: {
                files: {
                    './': ["<%= packageName %>", 'deploy/deploy.sh']
                },
                options: {
//                    host: '10.0.2.2',
                    host: '127.0.0.1',
                    port: 22000,
                    username: 'root',
                    password: 'mepcRul3Z',
                    path: '/var/www',
                    createDirectories: true
                }
            }
        },
        sshexec: {
            deploy: {
                command: "chmod +x /var/www/deploy/deploy.sh && /var/www/deploy/deploy.sh <%= packageName %>",
                options: {
//                    host: '10.0.2.2',
                    host: '127.0.0.1',
                    port: 22000,
                    username: 'root',
                    password: 'mepcRul3Z'
                }
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
        'sshexec:deploy'
    ]);

    grunt.registerTask('default', [
        'package',
        'deploy'
    ]);
};
