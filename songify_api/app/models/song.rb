class Song < ApplicationRecord
  belongs_to :album, optional: true
  belongs_to :artist
  belongs_to :user, optional: true

  validates_presence_of :title, :length, :artist
end
