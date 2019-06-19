class Song < ApplicationRecord
  belongs_to :album
  belongs_to :artists
  belongs_to :user

  validates_presence_of :title, :length
end
