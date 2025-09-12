// auth-helper.js - Place this in your static/js folder

class AuthHelper {
    static TOKEN_KEY = 'jwtToken';

    static getToken() {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    static setToken(token) {
        localStorage.setItem(this.TOKEN_KEY, token);
    }

    static removeToken() {
        localStorage.removeItem(this.TOKEN_KEY);
    }

    static isAuthenticated() {
        return !!this.getToken();
    }

    static redirectToLogin() {
        window.location.href = '/web/login';
    }

    static logout() {
        this.removeToken();
        this.redirectToLogin();
    }

    static checkAuthAndRedirect() {
        if (!this.isAuthenticated()) {
            this.redirectToLogin();
            return false;
        }
        return true;
    }

    static authenticatedFetch(url, options = {}) {
        const token = this.getToken();
        if (!token) {
            this.redirectToLogin();
            return Promise.reject(new Error('No token available'));
        }

        options.headers = options.headers || {};
        options.headers['Authorization'] = `Bearer ${token}`;

        if (!options.headers['Content-Type']) {
            options.headers['Content-Type'] = 'application/json';
        }

        return fetch(url, options)
            .then(response => {
                if (response.status === 401) {
                    this.logout();
                    throw new Error('Unauthorized - redirecting to login');
                }
                return response;
            });
    }

    static async authenticatedRequest(url, options = {}) {
        try {
            const response = await this.authenticatedFetch(url, options);

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }

            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                return await response.json();
            }

            return await response.text();
        } catch (error) {
            console.error('Authenticated request failed:', error);
            throw error;
        }
    }

    static setupGlobalFetchInterceptor() {
        const originalFetch = window.fetch;
        window.fetch = (url, options = {}) => {
            // Only intercept API calls
            if (url.startsWith('/api/') ||
                url.startsWith('/student/') ||
                url.startsWith('/teacher/') ||
                url.startsWith('/admin/') ||
                url.startsWith('/courses/') ||
                url.startsWith('/grades/') ||
                url.startsWith('/assignments/')) {
                return this.authenticatedFetch(url, options);
            }
            return originalFetch(url, options);
        };
    }
}

// Export for use in other scripts
if (typeof module !== 'undefined' && module.exports) {
    module.exports = AuthHelper;
}