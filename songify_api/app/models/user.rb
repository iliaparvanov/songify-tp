class User < ApplicationRecord

	# encrypt password
	has_secure_password

	has_many :songs, dependent: :destroy
	validates_presence_of :email, :name, :password_digest 
end
